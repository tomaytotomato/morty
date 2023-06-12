package com.kapango.application.view;

import com.kapango.application.appnav.AppNav;
import com.kapango.application.appnav.AppNavItem;
import com.kapango.application.dto.UserDto;
import com.kapango.application.security.AuthenticatedUser;
import com.kapango.application.view.admin.IncidentSeverityManager;
import com.kapango.application.view.admin.IncidentTypeManager;
import com.kapango.application.view.admin.TagManager;
import com.kapango.application.view.admin.UserManager;
import com.kapango.application.view.createapostmortem.CreateaPostmortemView;
import com.kapango.application.view.index.IndexView;
import com.kapango.application.view.integrations.IntegrationsView;
import com.kapango.application.view.postmortems.PostmortemsCollaborativeView;
import com.kapango.application.view.postmortems.PostmortemsView;
import com.kapango.application.view.statistics.StatisticsView;
import com.kapango.application.view.templates.TemplatesView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Morty");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Image mortyLogo = new Image("/images/morty.png", "Morty app logo");
        mortyLogo.addClassNames("drawer-logo");
        Header header = new Header(mortyLogo, appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        // Starting with v24.1, AppNav will be replaced with the official
        // SideNav component.
        AppNav nav = new AppNav();

        if (accessChecker.hasAccess(IndexView.class)) {
            nav.addItem(new AppNavItem("Index", IndexView.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(CreateaPostmortemView.class)) {
            nav.addItem(
                new AppNavItem("Create a Postmortem", CreateaPostmortemView.class, LineAwesomeIcon.EDIT.create()));

        }
        if (accessChecker.hasAccess(PostmortemsView.class)) {
            nav.addItem(new AppNavItem("Postmortems", PostmortemsView.class, LineAwesomeIcon.NEWSPAPER.create()));

        }
        if (accessChecker.hasAccess(PostmortemsCollaborativeView.class)) {
            nav.addItem(new AppNavItem("Postmortems-Collab", PostmortemsCollaborativeView.class, LineAwesomeIcon.NEWSPAPER.create()));

        }
        if (accessChecker.hasAccess(IntegrationsView.class)) {
            nav.addItem(new AppNavItem("Listeners", IntegrationsView.class, LineAwesomeIcon.PLUG_SOLID.create()));

        }
        if (accessChecker.hasAccess(TagManager.class)) {
            nav.addItem(new AppNavItem("Tag Manager", TagManager.class, LineAwesomeIcon.TAGS_SOLID.create()));
        }

        if (accessChecker.hasAccess(IncidentTypeManager.class)) {
            nav.addItem(new AppNavItem("Incident Type Manager", IncidentTypeManager.class, LineAwesomeIcon.EXPAND_SOLID.create()));
        }
        if (accessChecker.hasAccess(IncidentSeverityManager.class)) {
            nav.addItem(new AppNavItem("Incident Severity Manager", IncidentSeverityManager.class, LineAwesomeIcon.EXTERNAL_LINK_ALT_SOLID.create()));
        }

        if (accessChecker.hasAccess(UserManager.class)) {
            nav.addItem(new AppNavItem("User Management", UserManager.class, LineAwesomeIcon.USER.create()));
        }

        if (accessChecker.hasAccess(TemplatesView.class)) {
            nav.addItem(new AppNavItem("Templates", TemplatesView.class, LineAwesomeIcon.SHAPES_SOLID.create()));

        }
        if (accessChecker.hasAccess(StatisticsView.class)) {
            nav.addItem(new AppNavItem("Statistics", StatisticsView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        var maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserDto user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
