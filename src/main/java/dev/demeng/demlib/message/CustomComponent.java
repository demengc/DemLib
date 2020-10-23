package dev.demeng.demlib.message;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A very simple way of sending interactive chat messages. */
public final class CustomComponent {

  /** The past components having different hover/click events. */
  private final List<TextComponent> pastComponents = new ArrayList<>();

  /** The current component that is being modified. */
  private TextComponent currentComponent;

  /**
   * Create a new interactive chat component.
   *
   * @param text The text to edit.
   */
  private CustomComponent(String... text) {
    this.currentComponent = new TextComponent(String.join("\n", MessageUtils.colorize(text)));
  }

  /**
   * Add a show text hover event for the current component.
   *
   * @param text The text to show on hover
   * @return The new CustomComponent
   */
  public CustomComponent onHover(String... text) {
    return onHover(HoverEvent.Action.SHOW_TEXT, String.join("\n", text));
  }

  /**
   * Add a hover event for the current component.
   *
   * @param action The action to do on hover
   * @param text The text related to the event
   * @return The new CustomComponent
   */
  public CustomComponent onHover(HoverEvent.Action action, String text) {
    currentComponent.setHoverEvent(
        new HoverEvent(action, TextComponent.fromLegacyText(MessageUtils.colorize(text))));
    return this;
  }

  /**
   * Add a run command event for the current component.
   *
   * @param text The command to run
   * @return The new CustomComponent
   */
  public CustomComponent onClickRunCmd(String text) {
    return onClick(Action.RUN_COMMAND, text);
  }

  /**
   * Add a suggest command event for the current component.
   *
   * @param text The command to suggest
   * @return The new CustomComponent
   */
  public CustomComponent onClickSuggestCmd(String text) {
    return onClick(Action.SUGGEST_COMMAND, text);
  }

  /**
   * Open the given URL for the current component.
   *
   * @param url The url to open
   * @return The new CustomComponent
   */
  public CustomComponent onClickOpenUrl(String url) {
    return onClick(Action.OPEN_URL, url);
  }

  /**
   * Adds a click event for the current.
   *
   * @param action The action on click
   * @param text The text relating to the action
   * @return The new CustomComponent
   */
  public CustomComponent onClick(Action action, String text) {
    currentComponent.setClickEvent(new ClickEvent(action, MessageUtils.colorize(text)));

    return this;
  }

  /**
   * Create another component. The current is put in a list of past components so next time you use
   * onClick or onHover, you will be added the event to the new one specified here.
   *
   * @param text The text to append
   * @return The new CustomComponent
   */
  public CustomComponent append(String text) {
    pastComponents.add(currentComponent);
    currentComponent = new TextComponent(MessageUtils.colorize(text));

    return this;
  }

  /**
   * Form a single TextComponent out of all components created.
   *
   * @return The single TextComponent
   */
  public TextComponent build() {
    final TextComponent mainComponent = new TextComponent("");

    for (final TextComponent pastComponent : pastComponents) mainComponent.addExtra(pastComponent);

    mainComponent.addExtra(currentComponent);

    return mainComponent;
  }

  /**
   * Attempts to send the complete CustomComponent to the players.
   *
   * @param players Players to send the message to
   */
  public void send(Player... players) {
    final TextComponent mainComponent = build();

    for (final Player p : players) BungeeChatProvider.sendComponent(p, mainComponent);
  }

  /**
   * Create a new interactive chat component. You can then build upon your text to add interactive
   * elements.
   *
   * @param text The text to start building on
   * @return The newly created CustomComponent
   */
  public static CustomComponent of(String... text) {
    return new CustomComponent(text);
  }
}

/** A wrapper for Bungee Chat Component library. */
class BungeeChatProvider {

  static void sendComponent(Player p, Object comps) {

    if (comps instanceof TextComponent) BungeeChatProvider.sendComponent(p, comps);
    else sendComponent(p, (BaseComponent[]) comps);
  }

  private static void sendComponent(Player p, BaseComponent... comps) {
    String plainMessage = "";

    for (final BaseComponent comp : comps) plainMessage += comp.toLegacyText();

    tell(p, plainMessage);

    try {
      p.spigot().sendMessage(comps);
    } catch (NoClassDefFoundError | NoSuchMethodError | Exception ex) {
      tell(p, plainMessage);
    }
  }

  private static void tell(Player p, String msg) {
    Objects.requireNonNull(p, "Player cannot be null");

    if (msg.isEmpty() || "none".equals(msg)) return;

    final String stripped =
        msg.startsWith("[JSON]") ? msg.replaceFirst("\\[JSON\\]", "").trim() : msg;

    for (final String part : stripped.split("\n")) p.sendMessage(part);
  }
}
