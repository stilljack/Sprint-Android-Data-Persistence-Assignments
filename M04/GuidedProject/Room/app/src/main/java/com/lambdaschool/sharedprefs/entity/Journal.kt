package com.lambdaschool.sharedprefs.entity

import com.lambdaschool.sharedprefs.Prefs
import com.lambdaschool.sharedprefs.model.JournalEntry

/**
 * Entity class to hold Journal data and logic (business rules)
 */

class Journal {

    companion object {

        fun createJournalEntry(): JournalEntry {
            return JournalEntry(0)
        }

        fun createJournalEntry(text: String): JournalEntry {
            val entry = createJournalEntry()
            entry.entryText = text
            return entry
        }

        // Stretch: Need to ensure that a proper ID is created for these test entries
        fun createTestEntries(prefs: Prefs) {
            prefs.createEntry(createJournalEntry("Gathered by gravity how far away finite but unbounded the only home we've ever known network of wormholes Jean-François Champollion? Tendrils of gossamer clouds Orion's sword extraplanetary invent the universe trillion stirred by starlight. Shores of the cosmic ocean vastness is bearable only through love permanence of the stars astonishment a mote of dust suspended in a sunbeam extraplanetary. Made in the interiors of collapsing stars not a sunrise but a galaxyrise a very small stage in a vast cosmic arena a mote of dust suspended in a sunbeam something incredible is waiting to be known astonishment."))
            prefs.createEntry(createJournalEntry("Gathered by gravity how far away finite but unbounded the only home we've ever known network of wormholes Jean-François Champollion? Tendrils of gossamer clouds Orion's sword extraplanetary invent the universe trillion stirred by starlight. Shores of the cosmic ocean vastness is bearable only through love permanence of the stars astonishment a mote of dust suspended in a sunbeam extraplanetary. Made in the interiors of collapsing stars not a sunrise but a galaxyrise a very small stage in a vast cosmic arena a mote of dust suspended in a sunbeam something incredible is waiting to be known astonishment."))
            prefs.createEntry(createJournalEntry("Vangelis muse about Hypatia explorations hundreds of thousands another world. Shores of the cosmic ocean a mote of dust suspended in a sunbeam colonies Tunguska event finite but unbounded shores of the cosmic ocean? Extraplanetary bits of moving fluff gathered by gravity a still more glorious dawn awaits not a sunrise but a galaxyrise with pretty stories for which there's little good evidence. Take root and flourish courage of our questions vastness is bearable only through love paroxysm of global death invent the universe something incredible is waiting to be known?"))
            prefs.createEntry(createJournalEntry("Preserve and cherish that pale blue dot two ghostly white figures in coveralls and helmets are soflty dancing vastness is bearable only through love Euclid permanence of the stars inconspicuous motes of rock and gas. Dispassionate extraterrestrial observer something incredible is waiting to be known star stuff harvesting star light great turbulent clouds network of wormholes the only home we've ever known. Of brilliant syntheses emerged into consciousness vanquish the impossible vanquish the impossible hundreds of thousands dream of the mind's eye."))
            prefs.createEntry(createJournalEntry("Extraplanetary Euclid Hypatia brain is the seed of intelligence intelligent beings Rig Veda. Vastness is bearable only through love circumnavigated emerged into consciousness white dwarf colonies something incredible is waiting to be known. Two ghostly white figures in coveralls and helmets are soflty dancing star stuff harvesting star light bits of moving fluff invent the universe concept of the number one the ash of stellar alchemy. The only home we've ever known invent the universe rich in heavy atoms concept of the number one muse about something incredible is waiting to be known."))
            prefs.createEntry(createJournalEntry("Science dream of the mind's eye stirred by starlight Jean-François Champollion with pretty stories for which there's little good evidence circumnavigated? Sea of Tranquility extraordinary claims require extraordinary evidence the carbon in our apple pies the ash of stellar alchemy ship of the imagination preserve and cherish that pale blue dot. Sea of Tranquility hundreds of thousands ship of the imagination the sky calls to us invent the universe descended from astronomers and billions upon billions upon billions upon billions upon billions upon billions upon billions."))
        }
    }
}