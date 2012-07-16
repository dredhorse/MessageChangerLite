package net.breiden.spout.messagechanger.helper.commands;

import net.breiden.spout.messagechanger.commands.COMMANDS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for handling EnumCommand
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumCommand {
    public COMMANDS command();

}
