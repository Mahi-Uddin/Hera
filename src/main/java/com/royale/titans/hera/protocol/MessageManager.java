package com.royale.titans.hera.protocol;

import com.royale.titans.hera.crypto.sodium.Crypto;
import com.royale.titans.hera.protocol.messages.server.KeepAliveServerMessage;
import com.royale.titans.hera.protocol.messages.server.LoginFailedMessage;
import com.royale.titans.hera.protocol.messages.server.LoginOkMessage;
import com.royale.titans.hera.protocol.messages.server.ServerHelloMessage;
import com.royale.titans.hera.utils.Debugger;
import com.royale.titans.hera.utils.binary.ByteStream;

public class MessageManager {

    public static PiranhaMessage receiveMessage(short id, ByteStream stream) {
        PiranhaMessage message = null;

        ByteStream decrypted = Crypto.decrypt(id, stream);

        switch (id) {
            case 20100: {
                message = new ServerHelloMessage(decrypted);
                break;
            }
            case 20103: {
                message = new LoginFailedMessage(decrypted);
                break;
            }
            case 22194: {
                message = new LoginOkMessage(decrypted);
                break;
            }
            case 22700: {
                message = new KeepAliveServerMessage(decrypted);
                break;
            }
        }

        message.setId(id);

        Debugger.info("Received " + message.getClass().getSimpleName() + " (" + message.getId() + ")");

        message.process();

        return message;
    }
}
