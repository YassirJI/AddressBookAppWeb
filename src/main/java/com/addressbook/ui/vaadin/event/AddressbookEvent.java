package com.addressbook.ui.vaadin.event;

import com.addressbook.ui.vaadin.view.AddressbookViewType;

/*
 * Event bus events used in Addressbook are listed here as inner classes.
 */
public abstract class AddressbookEvent {

    public static final class UserLoginRequestedEvent {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName,
                final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static final class PostViewChangeEvent {
        private final AddressbookViewType view;

        public PostViewChangeEvent(final AddressbookViewType view) {
            this.view = view;
        }

        public AddressbookViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    }

}
