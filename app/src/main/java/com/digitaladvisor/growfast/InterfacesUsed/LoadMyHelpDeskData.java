package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.HelpEntry;

import java.util.List;

public interface LoadMyHelpDeskData {
    void onHelpDeskLoadSuccess(List<HelpEntry> text);

    void onHelpDeskFailure(String message);

}
