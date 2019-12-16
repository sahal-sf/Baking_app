package net.sahal.baking_app.Testing;

public class CheckIdlingResource {

    private static BakingTest sIdlingResource;

    public static BakingTest getIdlingResource() {
        if (sIdlingResource == null) {
            sIdlingResource = new BakingTest();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow) {
        if (sIdlingResource == null) {
            sIdlingResource = new BakingTest();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}
