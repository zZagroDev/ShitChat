package de.zagro.shitchat.ui.requests.outgoing;

import android.graphics.drawable.Drawable;

public class RequestOutgoingUser {

    private String name;
    private Drawable drawable;

    public RequestOutgoingUser(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
