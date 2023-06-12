package com.kapango.application.view.index;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;


public class NumberPanelComponent extends ListItem {

    private Integer value;
    private String title;
    private final String[] cssClasses;

    public NumberPanelComponent(String title, Integer value, String... cssClasess) {
        this.value = value;
        this.title = title;
        this.cssClasses = cssClasess;
        buildCard();
    }

    private void buildCard() {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM, BorderRadius.LARGE);

        var div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
            Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL, FontSize.XXXLARGE);
        div.setHeight("150px");
        div.setWidth("200px");

        div.addClassNames(Width.FULL, Display.FLEX);
        var number = new H1(value.toString());
        number.getElement().setAttribute("style", "font-size: 3.5em; height: inherit;");
        div.add(number);
        div.addClassNames(cssClasses);

        var header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);

        var description = new Paragraph(getText());
        description.addClassName(Margin.Vertical.MEDIUM);

        add(header, div);
    }

}