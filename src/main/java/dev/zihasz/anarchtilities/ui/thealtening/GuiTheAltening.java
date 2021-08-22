package dev.zihasz.anarchtilities.ui.thealtening;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import com.thealtening.api.retriever.BasicDataRetriever;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import dev.zihasz.anarchtilities.core.mixin.accessor.IMinecraft;
import dev.zihasz.anarchtilities.utils.SessionType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.net.Proxy;

public class GuiTheAltening extends GuiScreen {

	public static TheAlteningAuthentication authentication = TheAlteningAuthentication.mojang();
	public static BasicDataRetriever dataRetriever;
	private static String currentUsername;

	private final GuiScreen parentScreen;

	private GuiTextField txtToken;
	private GuiTextField txtApiKey;

	public GuiTheAltening(final GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	public void initGui() {
		this.txtToken = new GuiTextField(0, mc.fontRenderer, this.width / 2 - 100, this.height / 2 - (this.width / 8), 200, 20);
		this.txtApiKey = new GuiTextField(1, mc.fontRenderer, this.width / 2 - 100, this.height / 2 + (this.width / 8), 200, 20);

		this.addButton(new GuiButton(101, this.width / 2 - 100, this.height / 2 - (this.width / 8) + 25, "Login"));
		this.addButton(new GuiButton(103, this.width / 2 - 100, this.height / 2 + (this.width / 8) + 25, dataRetriever == null ? "Check" : "Generate"));

		this.addButton(new GuiButton(110, this.width / 2 - 100, this.height - (this.height / 8), "Back"));

		super.initGui();
	}

	public void updateScreen() {
		this.txtToken.updateCursorCounter();

		super.updateScreen();
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		this.drawCenteredString(this.fontRenderer, "TheAltening", this.width / 4, 7, 16777215);
		GL11.glPopMatrix();

		this.drawCenteredString(this.fontRenderer, "Username: " + currentUsername, this.width / 2, this.height / 8, 16777215);

		this.drawCenteredString(mc.fontRenderer, "Token", this.width / 2, this.height / 2 - (this.width / 8) - 15, 16777215);
		this.txtToken.drawTextBox();

		this.drawCenteredString(mc.fontRenderer, "API Key", this.width / 2, this.height / 2 + (this.width / 8) - 15, 16777215);
		this.txtApiKey.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 101)
			this.loginWithAlteningToken(this.txtToken.getText());
		if (button.id == 101) {
			if (dataRetriever == null) createRetriever(this.txtApiKey.getText());
			else generateAccount();
		}

		if (button.id == 110)
			mc.displayGuiScreen(parentScreen);

		super.actionPerformed(button);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.txtToken.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.txtToken.textboxKeyTyped(typedChar, keyCode);

		super.keyTyped(typedChar, keyCode);
	}

	public void loginWithAlteningToken(String token) {
		if (token.isEmpty())
			throw new IllegalArgumentException("Empty token!");

		try {
			authentication.updateService(AlteningServiceType.THEALTENING);

			YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
			YggdrasilUserAuthentication user = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
			user.setUsername(token);
			user.setPassword(token);
			user.logIn();

			Session session = new Session(
					user.getSelectedProfile().getName(),
					user.getSelectedProfile().getId().toString(),
					user.getAuthenticatedToken(),
					SessionType.LEGACY
			);
			this.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loginWithUsernamePassword(String username, String password) {
		if (username.isEmpty() || password.isEmpty())
			throw new IllegalArgumentException("Username or password is empty");

		try {
			YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
			YggdrasilUserAuthentication user = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
			user.setUsername(username);
			user.setPassword(password);
			user.logIn();

			Session session = new Session(
					user.getSelectedProfile().getName(),
					user.getSelectedProfile().getId().toString(),
					user.getAuthenticatedToken(),
					SessionType.LEGACY
			);
			this.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loginWithSession(AlteningServiceType serviceType, Session session) {
		if (session == null)
			throw new IllegalArgumentException("Session is null!");

		authentication.updateService(serviceType);
		this.setSession(session);
	}

	private void createRetriever(String apiKey) {
		try {
			dataRetriever = new BasicDataRetriever(apiKey);
		} catch (TheAlteningException e) {
			e.printStackTrace();
		}
	}
	private void generateAccount() {
		if (dataRetriever == null)
			throw new IllegalStateException("DataRetriever not initialised!");

		try {
			Account account = dataRetriever.getAccount();
			this.loginWithAlteningToken(account.getToken());
			// this.loginWithUsernamePassword(account.getUsername(), account.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSession(Session session) {
		((IMinecraft) mc).setSession(session);
		currentUsername = session.getUsername();
	}

}
