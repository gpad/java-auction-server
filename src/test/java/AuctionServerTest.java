import com.gpad.auctionserver.AuctionServerApplication;
import com.gpad.auctionserver.AuctionServerConfiguration;
import com.gpad.auctionserver.api.Auction;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gpad on 17/11/18.
 */
public class AuctionServerTest {

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

    @ClassRule
    public static final DropwizardAppRule<AuctionServerConfiguration> RULE = new DropwizardAppRule<>(
            AuctionServerApplication.class, CONFIG_PATH);

    @BeforeClass
    public static void migrateDb() throws Exception {
        RULE.getApplication().run();
    }

    @Test
    public void listOfAuctionsShouldAlwaysContainExampleAutions() throws Exception {
        final List<Auction> auctions = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/auctions")
                .request()
                .get(new GenericType<List<Auction>>() {
                });

        assertThat(auctions).contains(new Auction(1, "example_1"), new Auction(2, "example_2"), new Auction(3, "example_3"));
    }

    @Test
    public void getASingleAuctionByIdReturnAnAuctionObject() throws Exception {
        final Auction saying = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/auctions/666")
                .request()
                .get(Auction.class);

        assertThat(saying).isEqualTo(new Auction(666, "666"));
    }

    @Test
    public void getVersionShouldBeDefinedInConfiguration() throws Exception {
        final String version = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/version")
                .request()
                .get(String.class);

        assertThat(version).isEqualTo(RULE.getConfiguration().getAuctionServerVersion());
    }


    @Test
    public void testLogFileWritten() throws IOException {
        // The log file is using a size and time based policy, which used to silently
        // fail (and not write to a log file). This test ensures not only that the
        // log file exists, but also contains the log line that jetty prints on startup
        final Path log = Paths.get("./logs/application.log");
        assertThat(log).exists();
        final String actual = new String(Files.readAllBytes(log), UTF_8);
        assertThat(actual).contains("0.0.0.0:" + RULE.getLocalPort());
    }
}
