package com.avivba.simbanyu.domain.base

abstract class BaseUseCase<IN, OUT> {
    abstract suspend operator fun invoke(input: IN): OUT
}