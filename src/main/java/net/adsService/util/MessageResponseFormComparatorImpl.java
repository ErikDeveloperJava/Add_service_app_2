package net.adsService.util;

import net.adsService.form.MessageResponseForm;

import java.util.Comparator;

public class MessageResponseFormComparatorImpl implements Comparator<MessageResponseForm> {
    @Override
    public int compare(MessageResponseForm obj1, MessageResponseForm obj2) {
        return obj2.getCount() - obj1.getCount();
    }
}
