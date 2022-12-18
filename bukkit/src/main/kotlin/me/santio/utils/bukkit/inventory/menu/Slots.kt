package me.santio.utils.bukkit.inventory.menu

import me.santio.utils.bukkit.inventory.menu.menus.VirtualMenu

@Suppress("unused")
class Slots(
    private val slots: MutableSet<Int>,
    private var sorted: Boolean = true
) {

    companion object {

        @get:JvmName("ALL")
        @JvmStatic
        val ALL: Slots = rect(0, 9*9 - 1)

        @get:JvmName("NONE")
        @JvmStatic
        val NONE: Slots = Slots(mutableSetOf())

        @JvmStatic
        fun get(vararg slots: Int): Slots = Slots(slots.toMutableSet())

        @JvmStatic
        @JvmOverloads
        fun column(column: Int, margin: Int = 0): Slots {
            val slots = mutableSetOf<Int>()
            for (i in margin until 9-margin) {
                slots.add((column - 1) + (i * 9))
            }
            return Slots(slots)
        }

        @JvmStatic
        @JvmOverloads
        fun row(row: Int, margin: Int = 0): Slots {
            val slots = mutableSetOf<Int>()
            for (i in margin until 9-margin) {
                slots.add(i + ((row - 1) * 9))
            }
            return Slots(slots)
        }

        @JvmStatic
        fun rect(corner1: Int, corner2: Int): Slots {
            val slots = mutableSetOf<Int>()
            val oneCoord = getCords(minOf(corner1, corner2))
            val twoCoord = getCords(maxOf(corner1, corner2))

            for (y in oneCoord.second..twoCoord.second) {
                for (x in oneCoord.first..twoCoord.first) {
                    slots.add(x + (y * 9))
                }
            }

            return Slots(slots)
        }

        private fun getCords(slot: Int): Pair<Int, Int> {
            return Pair(slot % 9, slot / 9)
        }

    }

    fun size() = slots.size
    fun iterator(): Iterator<Int> = slots.iterator()
    fun isSorted() = sorted

    fun add(vararg slots: Int): Slots {
        slots.forEach { this.slots.add(it) }
        return this
    }

    fun remove(vararg slots: Int): Slots {
        slots.forEach { this.slots.remove(it) }
        return this
    }

    fun add(slots: Slots): Slots {
        this.slots.addAll(slots.slots)
        return this
    }

    fun remove(slots: Slots): Slots {
        this.slots.removeAll(slots.slots)
        return this
    }

    fun union(slots: Slots): Slots {
        return Slots(this.slots.union(slots.slots).toMutableSet())
    }

    fun sorted(): Slots {
        this.sorted = true
        return this
    }

    fun unsorted(): Slots {
        this.sorted = false
        return this
    }

    fun get(index: Int): Int? = slots.elementAtOrNull(index)

    fun apply(inventory: VirtualMenu): Set<Int> {
        val slots = if (sorted) this.slots.sorted() else this.slots
        return slots.filter { it < inventory.size() }.toSet()
    }

}