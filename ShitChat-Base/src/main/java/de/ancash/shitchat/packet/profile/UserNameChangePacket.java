package de.ancash.shitchat.packet.profile;

import java.util.UUID;

import de.ancash.shitchat.packet.SessionedPacket;

public class UserNameChangePacket extends SessionedPacket implements IProfilePacket {

	private static final long serialVersionUID = -8785640453975804471L;

	private final String newUser;

	public UserNameChangePacket(UUID sessionId, String newUser) {
		super(sessionId);
		this.newUser = newUser;
	}

	public String getNewUserName() {
		return newUser;
	}
}
