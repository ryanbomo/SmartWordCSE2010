# SmartWordCSE2010

This is the README.md for our CSE2010 Group Project.

## Goal

Our initial goal is to turn in a functional product.  Timing and space are irrelevant to our goal of delivery.

## Design

	 Description of the overall algorithm:
1. Grab word list and build trie from the word list [Done in SmartWord]

    a. These words are then inserted into the Trie

    b. General structure for trie is used, but instead of worrying about leaves, the last letter of each word is given the word weight and the word string. This allows the ends of words to be seen logically, but saves space in not needing blank nodes at the end of the trie.

2. Process the old messages by chopping the file into lines, and then the lines into words. [Done in processOldMessages]

    a.  check to make sure each word is valid

        -If the word has ANY non-alphabet characters, the word is ignored.

        -Currently an issue of ignoring the last word of a sentence, due to
         punctuation.

    b. each valid word is inserted

        - On insertion, if it's not in the trie, it is added

        - If it is in the trie, then the weight is incremented by 1

3. Guess by traversing the trie and returning highest weighted words that could possibly start with letters typed so far

    a. Unsure of implementation, currently using a hash table where children store
        word weight in the last letter of the word
        - Could implement a priority queue for this, so that we can pop the highest 
            priority possible guesses
4. Evaluate our accuracy
        If accurate, increment weight of word by 1
        If Inaccurate but target is in trie, increment weight of correct word by 1
        If Inaccurate but target is not in trie, add to trie and set weight to 1

## Implementation

## Testing

## Results - Initial Submission

## Changes to Initial Submission

## Results - Final Submission
