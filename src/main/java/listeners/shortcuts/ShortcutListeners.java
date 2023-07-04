package listeners.shortcuts;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.asOptions;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.option;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.block.element.BlockElements.staticSelect;
import static com.slack.api.model.view.Views.view;
import static com.slack.api.model.view.Views.viewClose;
import static com.slack.api.model.view.Views.viewSubmit;
import static com.slack.api.model.view.Views.viewTitle;

import com.slack.api.bolt.App;
import com.slack.api.model.view.View;
import listeners.ListenerProvider;
import utils.Constants;

public class ShortcutListeners implements ListenerProvider {
    @Override
    public void register(App app) {
        app.globalShortcut("category-action", (req, ctx) -> {
            try {
                var viewsOpenResponse = ctx.client()
                        .viewsOpen(r -> r.triggerId(ctx.getTriggerId()).view(buildView()));
                if (!viewsOpenResponse.isOk()) {
                    ctx.logger.error(viewsOpenResponse.toString());
                }
            } catch (Exception e) {
                ctx.logger.error("Failed to call views.open API (error: {})", e.getMessage(), e);
            }
            return ctx.ack();
        });
        //    app.globalShortcut("sample-shortcut-id", new SampleGlobalShortcutListener(app));
    }

    View buildView() {
        return view(view -> view.callbackId(Constants.CallbackIds.MEETING_ARRANGEMENT)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title ->
                        title.type("plain_text").text("Meeting Arrangement").emoji(true)))
                .submit(viewSubmit(
                        submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(
                        close -> close.type("plain_text").text("Cancel").emoji(true)))
                .privateMetadata("{}")
                .blocks(asBlocks(
                        section(section -> section.blockId(Constants.BlockIds.CATEGORY)
                                .text(markdownText("Select a category of the meeting!"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId(Constants.ActionIds.CATEGORY)
                                        .placeholder(plainText("Select a category"))
                                        .options(asOptions(
                                                option(plainText("Customer"), "customer"),
                                                option(plainText("Partner"), "partner"),
                                                option(plainText("Internal"), "internal")))))),
                        input(input -> input.blockId(Constants.BlockIds.AGENDA)
                                .element(plainTextInput(pti ->
                                        pti.actionId(Constants.ActionIds.AGENDA).multiline(true)))
                                .label(plainText(
                                        pt -> pt.text("Detailed Agenda").emoji(true)))))));
    }
}
