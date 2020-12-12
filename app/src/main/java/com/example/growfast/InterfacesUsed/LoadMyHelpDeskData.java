package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.HelpEntry;

import java.util.List;

public interface LoadMyHelpDeskData {
    void onHelpDeskLoadSuccess(List<HelpEntry> text);

    void onHelpDeskFailure(String message);

}
