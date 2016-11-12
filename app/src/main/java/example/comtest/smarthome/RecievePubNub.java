package example.comtest.smarthome;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static final String PREFS = "SmartHousePrefs";
    private Context context;
    private static RecievePubNub recievePubNub;

    //need to send applicationContext in order to use sharedprefs in a non activity class
    private RecievePubNub() {
    }

    public static RecievePubNub getInstance(){
        if(recievePubNub == null){
            recievePubNub = new RecievePubNub();
        }
        return recievePubNub;
    }

    public void setContext(Context context){
        this.context = context;
    }


    public void subscribe() {

        new Thread(new Runnable() {
            public void run() {

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
                                switch (status.getCategory()) {
                                    case PNConnectedCategory:
                                        // this €is expected for a subscribe, this means there is no error or issue whatsoever
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
                            String sensorId = jsonObj.getString("sensorId");
                            String valueId = jsonObj.getString("value");
                            System.out.println(message.getMessage().toString());

                            commandCheck(valueId, sensorId);

                            //commandCheck(commandId, slotId, deviceId);

                        } catch (JSONException e) {
                            System.out.println("JSONException: " + e);
                        }
                    }

                    @Override
                    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                        // handle incoming presence data

                    }
                });

                pubnub.subscribe().channels(Arrays.asList("hkr_channel_unit")).execute();
            }
        }).start();
    }

    private void commandCheck(String commandId, String sensorId) {
        int commandLength = commandId.length();
        String value = commandId.substring(5, commandLength);
        commandId = commandId.substring(0, 5);
        System.out.println("commandId: " + commandId + "       value: " + value);

        //initializing the shared prefs and the editor
        SharedPreferences SmartHousePrefs = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = SmartHousePrefs.edit();


        //This will just be a template/mock up since we do not have the correct commands yet
        if (commandId.equals("11000")) {
            //readAtticTemp   response = temp
            if (value.equals("X")) {
                // error
            } else {
                //value will hold the temperature
            }
        } else if (commandId.equals("11100")) {
            //setAtticTemp      response = ack
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("12000")) {
            // readRoomTemp     response = temp
            if (value.equals("X")) {
                // error
            } else {
                //value will hold the temperature
            }
        } else if (commandId.equals("12200")) {
            //setRoomTemp       response = ack
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("13000")) {
            //readOutTemp       response = temp
            if (value.equals("X")) {
                // error
            } else {
                //value will hold the temperature
            }
        } else if (commandId.equals("14000")) {
            //readPowerCunsumption      response = power
            if (value.equals("X")) {
                // error
            } else {
                //value will hold the power
            }
        } else if (commandId.equals("15000")) {
            //readFireAlarmStatus       response = 0 or 1 (1=fire)
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("16000")) {
            //readBurglarAlarmStatus    response = 0 or 1 (1 = burglar)
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("17000")) {
            //read water leakage status     response = 0 or 1 (1 = leakage)
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("18000")) {
            //read stove status     response = status
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("19000")) {
            //read window status    response = status
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("21000")) {
            //read power outage status      response = status
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("22000")) {
            //read attic fan status         response = status
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("25000")) {
            //read indoor light status      response = status
            if (value.equals("0")) {
                System.out.println("light is off");
                editor.putString("commandId", "off");
                editor.commit();

            } else if (value.equals("1")) {
                System.out.println("light is on");
                editor.putString("commandId", "on");
                editor.commit();

            } else if (value.equals("X")) {
                //X = error
            }
        } else if (commandId.equals("26000")) {
            //set indoor light      response = ack
            if (value.equals("0")) {
                System.out.println("light is off");

                //Saving the current state to the shared prefs
                editor.putString("commandId", "off");
                editor.commit();
            } else if (value.equals("1")) {
                System.out.println("light is on");

                //Saving the current state to the shared prefs
                editor.putString("commandId", "on");
                editor.commit();
            } else if (value.equals("X")) {
                //X = error
            }

        } else if (commandId.equals("27000")) {
            //read outdoor light        response = status
            if (value.equals("0")) {

            } else if (value.equals("1")) {

            } else if (value.equals("X")) {
                //X = error
            }
        }


    }

}
