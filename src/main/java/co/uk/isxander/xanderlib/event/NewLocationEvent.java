/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.event;

import co.uk.isxander.xanderlib.hypixel.locraw.LocationParsed;
import net.minecraftforge.fml.common.eventhandler.Event;

public class NewLocationEvent extends Event {

    public final LocationParsed location;

    public NewLocationEvent(LocationParsed location) {
        this.location = location;
    }

}
