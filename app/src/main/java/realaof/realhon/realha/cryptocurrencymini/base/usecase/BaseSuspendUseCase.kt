package realaof.realhon.realha.cryptocurrencymini.base.usecase

abstract class BaseSuspendUseCase<INPUT, OUTPUT> {

    protected abstract suspend fun create(input: INPUT): OUTPUT

    suspend fun execute(input: INPUT): Result<OUTPUT> = try {
        Result.success(create(input))
    } catch (error: Throwable) {
        Result.failure(error)
    }
}