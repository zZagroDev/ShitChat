package de.ancash.shitchat.server.listener.handler.profile;

import java.io.IOException;
import java.util.Arrays;

import de.ancash.shitchat.ShitChatPlaceholder;
import de.ancash.shitchat.packet.profile.PasswordChangePacket;
import de.ancash.shitchat.packet.profile.ProfileChangeResultPacket;
import de.ancash.shitchat.server.account.Account;
import de.ancash.shitchat.server.account.AccountRegistry;
import de.ancash.shitchat.server.client.Client;
import de.ancash.shitchat.server.listener.handler.HandlerUtil;
import de.ancash.sockets.packet.Packet;

public class PasswordChangeHandler {

	private final AccountRegistry registry;

	public PasswordChangeHandler(AccountRegistry registry) {
		this.registry = registry;
	}

	@SuppressWarnings("nls")
	public void changeUsername(Client client, PasswordChangePacket ucp, Packet packet) {
		System.out.println("password change packet: " + client.getRemoteAddress());
		if (!HandlerUtil.validateSID(registry, client, ucp, packet))
			return;
		Account acc = registry.getSession(ucp.getSessionId()).getAccount();
		if (acc == null) {
			packet.setSerializable(
					new ProfileChangeResultPacket(ucp.getSessionId(), null, ShitChatPlaceholder.INTERNAL_ERROR));
		} else {
			byte[] realOld = acc.getPassword();
			if (!Arrays.equals(realOld, ucp.getOldPass()) || ucp.getNewPass().length != 32) {
				packet.setSerializable(
						new ProfileChangeResultPacket(ucp.getSessionId(), null, ShitChatPlaceholder.WRONG_PASSWORD));
			} else {
				try {
					acc.setPassword(ucp.getNewPass());
					packet.setSerializable(new ProfileChangeResultPacket(ucp.getSessionId(), acc.toUser(), null));
				} catch (IOException e) {
					packet.setSerializable(new ProfileChangeResultPacket(ucp.getSessionId(), null,
							ShitChatPlaceholder.INTERNAL_ERROR));
					System.err.println("could not change password of " + acc.getId());
					e.printStackTrace();
				}
			}
		}

		client.putWrite(packet.toBytes());
	}
}
