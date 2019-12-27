package com.demeng7215.demlib.api.messages.centeredtext;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Utility to send centered messages to players.
 */
public class CenteredMessage {

    private static final int CENTER_PX = 154;

    public static void send(Player p, String... messages) {

        for (String message : messages) {

            if (message == null || message.equals("")) p.sendMessage("");
            message = ChatColor.translateAlternateColorCodes('&', message);

            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : message.toCharArray()) {

                if (c == '§') {
                    previousCode = true;

                } else if (previousCode) {
                    previousCode = false;
                    isBold = c == 'l' || c == 'L';

                } else {
                    FontInfo dFI = FontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            int toCompensate = CENTER_PX - halvedMessageSize;
            int spaceLength = FontInfo.SPACE.getLength() + 1;
            int compensated = 0;

            StringBuilder sb = new StringBuilder();

            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }

            p.sendMessage(sb.toString() + message);
        }
    }
}
