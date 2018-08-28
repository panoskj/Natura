package com.progwml6.natura.overworld.block.crops;

import java.util.Random;
import java.lang.UnsupportedOperationException;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public abstract class BlockOverworldCrops extends BlockCrops // implements IGrowable
{
    protected BlockOverworldCrops()
    {
        super();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
    }

    @Override
    protected Item getSeed()
    {
        throw new UnsupportedOperationException("Use getSeedStack() instead.");
    }

    @Override
    protected Item getCrop()
    {
        throw new UnsupportedOperationException("Use getCropStack() instead.");
    }

    protected abstract ItemStack getSeedStack();

    protected abstract ItemStack getCropStack();

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        int age = this.getAge(state);
        if (age >= this.getMaxAge())
        {
            Random rand = world instanceof World ? ((World)world).rand : new Random();
            drops.add(this.getCropStack());
            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * this.getMaxAge()) <= age)
                {
                    drops.add(this.getSeedStack());
                }
            }
        }
        else drops.add(this.getSeedStack());
    }

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isMaxAge(state) ? this.getCropStack().getItem() : this.getSeedStack().getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return this.getSeedStack();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.isMaxAge(state) ? this.getCropStack().getMetadata() : this.getSeedStack().getMetadata();
    }
}
