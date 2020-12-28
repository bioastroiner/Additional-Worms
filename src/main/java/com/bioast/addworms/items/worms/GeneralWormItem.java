package com.bioast.addworms.items.worms;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import net.minecraft.world.World;

import java.util.function.Function;

/**
 *
 * almost all worms should use this class
 * and don't need to have their own class for items
 * (we have main feutures in abstract class is for situtation that we don't follow this rule)
 *
 * for entites however it's nicer for them to have different Class for each
 * later for tiering they can share the same class but their NBT may differ
 * in those situtations we would try different EntityTypes(same class) or just use
 * bare-bone NBT difference(same class-same type).
 *
 * currentlly our renderer is bound mainly by the class, tough we can technically use NBT
 * datas to change it in realtime, the wormRenderer classes are quite Abstract,
 * // TODO may use Baked models in future
 *
 */
public class GeneralWormItem extends AbstractWormItem {

    public GeneralWormItem(Properties properties, Function<World, ?
            extends AbstractWormEntity> func) {
        super(properties, func);
    }
}
