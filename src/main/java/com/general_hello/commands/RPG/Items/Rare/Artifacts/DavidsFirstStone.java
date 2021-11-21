package com.general_hello.commands.RPG.Items.Rare.Artifacts;

import com.general_hello.commands.RPG.Objects.Artifacts;
import com.general_hello.commands.RPG.Types.AttainableBy;
import com.general_hello.commands.RPG.Types.Rarity;

import java.util.ArrayList;

public class DavidsFirstStone extends Artifacts {
    private final String collectionName;
    private final ArrayList<Artifacts> collectionNeeded = new ArrayList<>();
    public DavidsFirstStone() {
        super(Rarity.RARE, true, "David's First Stone", AttainableBy.CRAFTING, "");
        collectionName = "David's Stones";
        collectionNeeded.add(new DavidsFirstStone());
        collectionNeeded.add(new DavidsSecondStone());
        collectionNeeded.add(new DavidsThirdStone());
        collectionNeeded.add(new DavidsFourthStone());
        collectionNeeded.add(new DavidsFifthStone());
    }

    public String getCollectionName() {
        return collectionName;
    }

    public ArrayList<Artifacts> getCollectionNeeded() {
        return collectionNeeded;
    }
}