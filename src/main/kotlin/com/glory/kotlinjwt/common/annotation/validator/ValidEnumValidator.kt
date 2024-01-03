package com.glory.kotlinjwt.common.annotation.validator

import com.glory.kotlinjwt.common.annotation.ValidEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class ValidEnumValidator : ConstraintValidator<ValidEnum, Any> {
    private lateinit var enumValues: Array<out Enum<*>>

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        value ?: return true

        return enumValues.any { it.name == value.toString() }
    }
}
