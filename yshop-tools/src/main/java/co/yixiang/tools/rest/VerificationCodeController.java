/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.tools.rest;

import co.yixiang.tools.service.EmailConfigService;
import co.yixiang.utils.YshopConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import co.yixiang.tools.domain.VerificationCode;
import co.yixiang.tools.domain.vo.EmailVo;
import co.yixiang.tools.service.VerificationCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hupeng
 * @date 2018-12-26
 */
@RestController
@RequestMapping("/api/code")
@Api(tags = "工具：验证码管理")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    private final EmailConfigService emailService;

    public VerificationCodeController(VerificationCodeService verificationCodeService,  EmailConfigService emailService) {
        this.verificationCodeService = verificationCodeService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public ResponseEntity<Object> resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(YshopConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public ResponseEntity<Object> resetPass(@RequestParam String email) throws Exception {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(YshopConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public ResponseEntity<Object> validated(VerificationCode code){
        verificationCodeService.validated(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
