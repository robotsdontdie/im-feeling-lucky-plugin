package io.jenkins.plugins.imfeelinglucky;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.Queue;
import hudson.model.RootAction;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.interceptor.RequirePOST;
import org.kohsuke.stapler.verb.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Extension
public class ImFeelingLuckyRootAction implements RootAction {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final Logger logger = Logger.getLogger(ImFeelingLuckyRootAction.class.getName());

    @Override
    public String getIconFileName() {
        return "plugin/im-feeling-lucky/icons/lucky.png";
    }

    @Override
    public String getDisplayName() {
        return "I'm feeling Lucky";
    }

    @Override
    public String getUrlName() {
        return "im-feeling-lucky";
    }

    public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException {
        scheduleRandomJob();
        rsp.sendRedirect2("../");
    }

    private void scheduleRandomJob() {
        Jenkins jenkins = Jenkins.getInstanceOrNull();
        if (jenkins == null) {
            return;
        }

        List<Item> items = jenkins.getAllItems();
        List<Queue.Task> tasks = new ArrayList<>();
        for (Item item : items) {
            if (!item.hasPermission(Item.BUILD)) {
                logger.fine("Skipping item due to lack of permissions: " + item.getFullDisplayName());
                continue;
            }

            if (!(item instanceof Queue.Task)) {
                logger.fine("Skipping item as it is not a Queue.Task: " + item.getFullDisplayName());
                continue;
            }

            tasks.add((Queue.Task) item);
        }

        if (tasks.size() > 0)
        {
            int index = random.nextInt(tasks.size());
            jenkins.getQueue().schedule(tasks.get(index), 0);
            logger.info("Triggered task: " + tasks.get(index).getFullDisplayName());
        } else {
            logger.info("No available tasks to be triggered.");
        }
    }
}
