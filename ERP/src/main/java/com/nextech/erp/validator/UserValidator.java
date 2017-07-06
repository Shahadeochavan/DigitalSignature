package com.nextech.erp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nextech.erp.model.User;

public class UserValidator implements Validator {


	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	public void validate(Object o, Errors errors) {
		User user = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"NotEmpty");
		if (user.getUserid().length() < 6 || user.getUserid().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!user.getPassword().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm",
					"Diff.userForm.passwordConfirm");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname",
				"NotEmpty");
		if (user.getFirstName().length() < 1
				|| user.getFirstName().length() > 20) {
			errors.rejectValue("firstname", "Size.userForm.firstname");
		}
		if (user.getLastName().length() < 1 || user.getLastName().length() > 20) {
			errors.rejectValue("lastname", "Size.userForm.lastname");
		}
		if (user.getMobile().length() < 6 || user.getMobile().length() >= 10) {
			errors.rejectValue("mobile", "Size.userForm.mobile");
		}
	}
}
