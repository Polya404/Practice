package org.dobmax.user.pool

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-up")
class SignUpController(val userPoolService: UserPoolService) {
    @PostMapping
    fun signUp(
        @RequestBody newUser: SignUpRequestDto,
    ) {
        userPoolService.signUp(newUser)
    }

    @PostMapping
    @RequestMapping("/confirm")
    fun confirm(
        @RequestBody confirmSignUp: ConfirmSignUpRequestDto,
    ) {
        userPoolService.confirm(confirmSignUp)
    }
}
