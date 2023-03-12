package me.santio.utils.nms;

import me.santio.utils.nms.packets.PacketType;
import me.santio.utils.nms.packets.WrappedPacket;

public class test {
    
    public static void main(String[] args) {
        WrappedPacket packet = new WrappedPacket(PacketType.Play.Server.PING());
        packet.writeInt(0, 1);
        packet.broadcast();
    }
    
}
