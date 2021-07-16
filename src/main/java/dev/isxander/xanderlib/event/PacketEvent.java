/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package dev.isxander.xanderlib.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent extends Event {

    private final Packet<?> packet;
    private final Type type;

    private PacketEvent(Packet<?> packet, Type type) {
        this.packet = packet;
        this.type = type;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        INCOMING,
        OUTGOING
    }

    public static final class Incoming extends PacketEvent {
        public Incoming(final Packet<?> packet) {
            super(packet, Type.INCOMING);
        }
    }

    public static final class Outgoing extends PacketEvent {
        public Outgoing(final Packet<?> packet) {
            super(packet, Type.OUTGOING);
        }
    }

}
