package es.laura.saborYNoche.enums;

public enum RoleEnum {
    USER("ROLE_USER"),
    EMPRESARIO("ROLE_EMPRESARIO");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
