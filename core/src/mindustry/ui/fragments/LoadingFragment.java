package mindustry.ui.fragments;

import arc.func.*;
import arc.graphics.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.graphics.*;
import mindustry.ui.*;

public class LoadingFragment extends Fragment{
    private Table table;
    private TextButton button;
    private Bar bar;

    @Override
    public void build(Group parent){
        parent.fill(Styles.black8, t -> {
            t.visible(false);
            t.touchable(Touchable.enabled);
            t.add().height(133f).row();
            t.add(new WarningBar()).growX().height(24f);
            t.row();
            t.add("$loading").name("namelabel").pad(10f).style(Styles.techLabel);
            t.row();
            t.add(new WarningBar()).growX().height(24f);
            t.row();

            bar = t.add(new Bar()).pad(3).size(500f, 40f).visible(false).get();
            t.row();
            button = t.button("$cancel", () -> {}).pad(20).size(250f, 70f).visible(false).get();
            table = t;
        });
    }

    public void setProgress(Floatp progress){
        bar.reset(0f);
        bar.visible(true);
        bar.set(() -> ((int)(progress.get() * 100) + "%"), progress, Pal.accent);
    }

    public void setButton(Runnable listener){
        button.visible(true);
        button.getListeners().remove(button.getListeners().size - 1);
        button.clicked(listener);
    }

    public void setText(String text){
        table.<Label>find("namelabel").setText(text);
        table.<Label>find("namelabel").setColor(Pal.accent);
    }

    public void show(){
        show("$loading");
    }

    public void show(String text){
        table.<Label>find("namelabel").setColor(Color.white);
        bar.visible(false);
        table.clearActions();
        table.touchable(Touchable.enabled);
        table.<Label>find("namelabel").setText(text);
        table.visible(true);
        table.getColor().a = 1f;
        table.toFront();
    }

    public void hide(){
        table.clearActions();
        table.toFront();
        table.touchable(Touchable.disabled);
        table.actions(Actions.fadeOut(0.5f), Actions.visible(false));
    }
}
