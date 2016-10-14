package example.comtest.smarthome;

import com.fasterxml.jackson.databind.JsonNode;
import com.pubnub.api.*;
import org.json.*;

import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

/**
 * Created by Sweetpink on 2016-10-08.
 */



public class RecievePubNub {

    public void subscribe(){
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-57c88d10-7fe9-11e6-82db-0619f8945a4f");
        pnConfiguration.setPublishKey("pub-c-f97a90e1-2aa2-4db6-aee7-6187431f9dff");
        pnConfiguration.setSecure(false);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override

            public void status(PubNub pubnub, PNStatus status) {
                // the status object returned is always related to subscribe but could contain
                // information about subscribe, heartbeat, or errors
                // use the operationType to switch on different options
                switch (status.getOperation()) {
                    // let's combine unsubscribe and subscribe handling for ease of use
                    case PNSubscribeOperation:
                    case PNUnsubscribeOperation:
                        // note: subscribe statuses never have traditional
                        // errors, they just have categories to represent the
                        // different issues or successes that occur as part of subscribe
                        switch(status.getCategory()) {
                            case PNConnectedCategory:
                                // this is expected for a subscribe, this means there is no error or issue whatsoever
                            case PNReconnectedCategory:
                                // this usually occurs if subscribe temporarily fails but reconnects. This means
                                // there was an error but there is no longer any issue
                            case PNDisconnectedCategory:
                                // this is the expected category for an unsubscribe. This means there
                                // was no error in unsubscribing from everything
                            case PNUnexpectedDisconnectCategory:
                                // this is usually an issue with the internet connection, this is an error, handle appropriately
                                // retry will be called automatically
                            case PNAccessDeniedCategory:
                                // this means that PAM does allow this client to subscribe to this
                                // channel and channel group configuration. This is another explicit error
                            default:
                                // More errors can be directly specified by creating explicit cases for other
                                // error categories of `PNStatusCategory` such as `PNTimeoutCategory` or `PNMalformedFilterExpressionCategory` or `PNDecryptionErrorCategory`
                        }

                    case PNHeartbeatOperation:
                        // heartbeat operations can in fact have errors, so it is important to check first for an error.
                        // For more information on how to configure heartbeat notifications through the status
                        // PNObjectEventListener callback, consult <link to the PNCONFIGURATION heartbeart config>
                        if (status.isError()) {
                            // There was an error with the heartbeat operation, handle here
                        } else {
                            // heartbeat operation was successful
                        }
                    default: {
                        // Encountered unknown status type
                    }
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // handle incoming messages

                try {
                    JSONObject jsonObj = new JSONObject(message.getMessage().toString());
                    String commandId = jsonObj.getString("commandId");
                    String slotId = jsonObj.getString("slotId");
                    String deviceId = jsonObj.getString("deviceId");

                    commandCheck(commandId, slotId, deviceId);

                }catch(JSONException e){
                    System.out.println("JSONException: " + e);
                }
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                // handle incoming presence data

            }
        });

        pubnub.subscribe().channels(Arrays.asList("hkr_channel")).execute();

    }

    private void commandCheck(String commandId, String slotId, String deviceId){
        //This will just be a template/mock up since we do not have the correct commands yet
        if(commandId.equals("X")){
            //whatever happends here we can add stuff to the sharedPreferences when we have the commands
        }else if(commandId.equals("Y")){

        }
    }

}
