import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import listeners.Listeners;

public class Main {

    public static void main(String[] args) throws Exception {
        // App expects an env variable: SLACK_BOT_TOKEN
        /*        System.setProperty(
                "SLACK_APP_TOKEN",
                "xapp-1-A05ET858P5Y-5514854223843-4556dd50868d1032863002635ee1d6d1f43c663591d2138f8869f6cb3c8226ce");
        System.setProperty("SLACK_BOT_TOKEN", "xxoxb-6874260548-5505339657238-ksxCPWAdTeMJdMIxua46RaHf");*/
        System.setProperty(
                "SLACK_APP_TOKEN",
                "xapp-1-A05EYTYRF63-5526044184100-6253df60acd5b1f63c3abf0dc637b1fcf4432b93a4c83018e65c58c5bc552ef6");
        System.setProperty("SLACK_BOT_TOKEN", "xoxb-5508942682455-5523372526787-nVf3c53Pfras9Bq011v1Q1jV");

        var app = new App();
        Listeners.register(app);
        // var server = new SlackAppServer(app);
        // server.start();
        // SocketModeApp expects an env variable: SLACK_APP_TOKEN
        new SocketModeApp(app).start();
    }
}
