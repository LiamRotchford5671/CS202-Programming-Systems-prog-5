# CS202-Programming-Systems-prog-5
Program 5 under Karla Fant at Portland State University


Description:

This program is a updated version of CS202 Programming Systems prog 4, in this version with the map already finished and loaded from prog 4 we utilize the player choices and a seperate data structure to hold a history bank of the player as they go through the map. This program is esstianlly a text based adventure game, at each move we are given the choice to move to the left or right and then are given a event space, (a decision, chance, spell, or end game). The player is given stats that are effected by the events on the map spaces.

This Program was built using intelliJ IDE

Data Structure: Binary Search Tree & linear linked list of arrays

      Heirarchy 1:    (journey is abstact base class)
      
      Journey            Journey         Journey            Journey
          |                  |               |                  |
          |                  |               |                  |
       chance             decision         end game           spell

      Heirarchy 2:
      
      Manager ------ bnode

      Heirarchy 3: 
      
      List -----listnode
