package br.com.iandev.midiaindoor.core.integration;

/**
 * Created by Lucas on 21/12/2016.
 */

public enum IntegrationApi {
    BDO("OBSERVADOR");

    private String name;

    IntegrationApi(String name) {
        this.name = name;
    }

    public static IntegrationApi fromValue(String name) {
        for (IntegrationApi integrationApi : IntegrationApi.values()) {
            if (integrationApi.getName().equals(name)) {
                return integrationApi;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
