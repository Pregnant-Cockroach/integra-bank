package com.bank.integra.enums;

public enum EmailValidationResponse {
    /**
     * Валидация прошла успешно.
     */
    OK("Email OK.", false), // <-- Текст и флаг успешности

    /**
     * Email адрес недействителен (например, не соответствует формату).
     */
    INVALID_FORMAT("Given email address has invalid format.", true),

    /**
     * Email адрес уже зарегистрирован.
     */
    ALREADY_TAKEN("Given email address is already taken.", true),

    /**
     * Email адрес идентичный.
     */
    EMAIL_IS_SAME_AS_CURRENT("Given email address is same as current.", true),

    /**
     * Email адрес существует, но помечен как нежелательный (например, временный email).
     */
    DISPOSABLE_EMAIL("Email address is temporary or disposable", true),

    /**
     * Email адрес не существует на почтовом сервере.
     */
    EMAIL_NOT_EXIST("Given email address doesn't exist on the server.", true),

    /**
     * Email слишком длинный.
     */
    TOO_LONG("Given email address is too long.", true),

    /**
     * Общая ошибка валидации, не попадающая под другие категории.
     */
    UNKNOWN_ERROR("Unknown error occurred during email validation.", true);


    // Поле для текстового описания
    private final String description;
    // Флаг, указывающий, является ли этот статус ошибкой
    private boolean isError;

    // Конструктор enum (всегда private)
    private EmailValidationResponse(String description, boolean isError) {
        this.description = description;
        this.isError = isError;
    }

    // Геттеры для полей
    public String getDescription() {
        return description;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    // Дополнительный метод для удобства, если нужна проверка на успех
    public boolean isSuccess() {
        return !isError;
    }
}
