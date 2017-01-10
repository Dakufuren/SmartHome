package example.comtest.smarthome;

import android.content.Context;
import android.content.SharedPreferences;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import org.json.JSONException;
import org.json.JSONObject;

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
                        System.out.println("Incoming message in PUBNUB!!!!!");
                        try {
                            System.out.println(message.getMessage().toString());
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
        System.out.println("PubNub  CommandID:    " + commandId);
        int commandLength = commandId.length();
        System.out.println("CommandLength:         " + commandLength);
        String value = commandId.substring(5, commandLength);
        commandId = commandId.substring(0, 5);
        System.out.println("commandId: " + commandId + "       value: " + value);

        //initializing the shared prefs and the editor
        SharedPreferences SmartHousePrefs = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = SmartHousePrefs.edit();


        //This will just be a template/mock up since we do not have the correct commands yet
        if (commandId.equals("11000")) {
            //readAtticTemp   response = temp
            if (value.equals("000X")) {
                // error
            } else {
                changeStateOfSensor(commandId, convertTemp(value));
                //value will hold the temperature
            }
        } else if (commandId.equals("11100")) {
            //setAtticTemp      response = ack
            if (value.equals("0000")) {
                changeStateOfSensor(value, sensorId);
            } else if (value.equals("0001")) {
                changeStateOfSensor(value, sensorId);
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("12000")) {
            // readRoomTemp     response = temp
            if (value.equals("000X")) {
                // error
            } else {
                changeStateOfSensor(commandId, convertTemp(value));
                //editor.putString("roomTemperature", value.toString());
                //editor.commit();
                //value will hold the temperature
            }
        } else if (commandId.equals("12200")) {
            //setRoomTemp       response = ack
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("13000")) {
            //readOutTemp       response = temp
            if (value.equals("000X")) {
                // error
            } else {
                changeStateOfSensor(commandId, convertTemp(value));
                //value will hold the temperature
            }
        } else if (commandId.equals("14000")) {
            //readPowerCunsumption      response = power
            if (value.equals("000X")) {
                // error
            } else {
                changeStateOfSensor(commandId, getValue(value));
                //value will hold the power
            }
        } else if (commandId.equals("15000")) {
            //readFireAlarmStatus       response = 0 or 1 (1=fire)
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));

            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));

            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("16000")) {
            //readBurglarAlarmStatus    response = 0 or 1 (1 = burglar)
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("17000")) {
            //read water leakage status     response = 0 or 1 (1 = leakage)
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("18000")) {
            //read stove status     response = status
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("19000")) {
            //read window status    response = status
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("21000")) {
            //read power outage status      response = status
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("22000")) {
            //read attic fan status         response = status
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("25000")) {
            //read indoor light status      response = status
            if (value.equals("0000")) {
                /*System.out.println("light is off");
                editor.putString("commandId", "off");
                editor.commit();
                */
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                /*System.out.println("light is on");
                editor.putString("commandId", "on");
                editor.commit();
                */
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("26000")) {
            //set indoor light      response = ack
            if (value.equals("0000")) {
                /*System.out.println("light is off");

                //Saving the current state to the shared prefs
                editor.putString("commandId", "off");
                editor.commit();*/

                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                /*System.out.println("light is on");

                //Saving the current state to the shared prefs
                editor.putString("commandId", "on");
                editor.commit();*/

                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }

        } else if (commandId.equals("27000")) {
            //read outdoor light        response = status
            if (value.equals("0000")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("0001")) {
                changeStateOfSensor(commandId, getValue(value));
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("34000")) {
            //read outdoor light        response = status
            if (value.equals("0000")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.leakage, "Water Leakage!", "Water everywhere");
                fireNotification.startNotification();
            } else if (value.equals("0001")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.leakage, "Water Leakage!", "Water everywhere");
                fireNotification.startNotification();
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("35000")) {
            //read outdoor light        response = status
            if (value.equals("0000")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.fire, "Fire!", "The roof is on fire");
                fireNotification.startNotification();
            } else if (value.equals("0001")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.fire, "Fire!", "The roof is on fire");
                fireNotification.startNotification();
            } else if (value.equals("000X")) {
                //X = error
            }
        } else if (commandId.equals("36000")) {
            //read outdoor light        response = status
            if (value.equals("0000")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.burglar, "BURGLARS!", "The burglar is in your house!");
                fireNotification.startNotification();
            } else if (value.equals("0001")) {
                NotificationHandler fireNotification = new NotificationHandler(context, R.drawable.burglar, "BURGLARS!", "The burglar is in your house!");
                fireNotification.startNotification();
            } else if (value.equals("000X")) {
                //X = error
            }
        }
    }

    private void changeStateOfSensor(String command, String value){
        String sensorType = "";
        if(command.equalsIgnoreCase("11000")){
            sensorType = "atticTemp";
        }else if(command.equalsIgnoreCase("11100")){
            sensorType = "atticTemp";
        }else if(command.equalsIgnoreCase("12000")){
            sensorType = "roomTemp";
        }else if(command.equalsIgnoreCase("12200")){
            sensorType = "roomTemp";
        }else if(command.equalsIgnoreCase("13000")){
            sensorType = "outdoorTemp";
        }else if(command.equalsIgnoreCase("14000")){
            sensorType = "powerConsumption";
        }else if(command.equalsIgnoreCase("16000")){
            sensorType = "alarm";
        }else if(command.equalsIgnoreCase("17000")){
            sensorType = "alarm";
        }else if(command.equalsIgnoreCase("18000")){
            sensorType = "stove";
        }else if(command.equalsIgnoreCase("19000")){
            sensorType = "window";
        }else if(command.equalsIgnoreCase("22000")){
            sensorType = "atticFan";
        }else if(command.equalsIgnoreCase("23000")){
            sensorType = "atticFan";
        }else if(command.equalsIgnoreCase("25000")){
            sensorType = "indoorLamp";
        }else if(command.equalsIgnoreCase("26000")){
            sensorType = "indoorLamp";
        }else if(command.equalsIgnoreCase("27000")){
            sensorType = "outdoorLamp";
        }else if(command.equalsIgnoreCase("28000")){
            sensorType = "outdoorLamp";
        }
        for(SensorInfo sensor : DataStorage.getInstance().getSensorList()){
            if(sensor.getType().equalsIgnoreCase(sensorType)){
                sensor.setValue(value);
                System.out.println("SensorType: " + sensor.getType() + "  Value: " + sensor.getValue());

            }
        }
    }

    private String convertTemp(String value){
        String trueTemp = "";
        int temp = (5 * Integer.parseInt(value) * 100)/1024;
        System.out.println("Method: convertTemp, realTemp: " + temp + " Value: " + value);

        return temp + "";
    }

    private String getValue(String inValue){
        String value = inValue.substring(3,4);
        System.out.println("InValue: " + inValue + "value: " + value);
        return value;
    }

}
