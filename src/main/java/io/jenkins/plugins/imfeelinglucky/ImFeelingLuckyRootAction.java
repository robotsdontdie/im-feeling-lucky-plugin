package io.jenkins.plugins.imfeelinglucky;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.Queue;
import hudson.model.RootAction;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Extension
public class ImFeelingLuckyRootAction implements RootAction {
    private static final Random random = new Random(System.currentTimeMillis());

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
            if (item instanceof Queue.Task) {
                tasks.add((Queue.Task) item);
            }
        }

        if (tasks.size() > 0)
        {
            int index = random.nextInt(tasks.size());
            jenkins.getQueue().schedule(tasks.get(index), 0);
        }
    }
}
