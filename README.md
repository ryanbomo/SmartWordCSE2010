# SmartWordCSE2010

This is the README.md for our CSE2010 Group Project.

## Goal

Our initial goal is to turn in a functional product.  Timing and space are irrelevant to our goal of delivery.

Goal for final Product
	Approach an accuracy of 50
	Cut down on space needed.

## Design

#### Trie Layout

	Inserting word w that's not already in our Trie:

                w = "dips"
                trie =                  d,0
                                       /    \
                                      i,0    o,1,"do"
                                     /   \    
                               p,1"dip"   r,0           
                                           \
                                            t,1,"dirt"
                                              
                                                     
                trie.insert(w,1)
                trie =                  d,0
                                       /    \
                                      i,0    o,1,"do"
                                     /   \     
                              p,1,"dip"   r,0           
                                     \      \
                               s,1,"dips"    t,1,"dirt"
	
	IF ALREADY IN TRIE:

                w = "dips"
                trie =                  d,0
                                       /    \
                                      i,0    o,1,"do"
                                     /   \     
                              p,1,"dip"   r,0           
                                     \      \
                               s,1,"dips"    t,1,"dirt"
                                              
                                                     
                trie.insert(w,1)
                trie =                  d,0
                                       /    \
                                      i,0    o,1,"do"
                                     /   \     
                              p,1,"dip"   r,0           
                                     \      \
                               s,2,"dips"    t,1,"dirt"

## Implementation

####Description of the overall algorithm:

1. Grab word list and build trie from the word list [Done in SmartWord]
  1. These words are then inserted into the Trie
  2. General structure for trie is used, but instead of worrying about leaves, the last letter of each word is given the word weight and the word string. This allows the ends of words to be seen logically, but saves space in not needing blank nodes at the end of the trie.

2. Process the old messages by chopping the file into lines, and then the lines into words. [Done in processOldMessages]
  1.  check to make sure each word is valid
    - If the word has ANY non-alphabet characters, the word is ignored.
    - Currently an issue of ignoring the last word of a sentence, due to punctuation.
  2. each valid word is inserted
    - On insertion, if it's not in the trie, it is added
    - If it is in the trie, then the weight is incremented by 1

3. Guess by traversing the trie and returning highest weighted words that could possibly start with letters typed so far
  1. given a letter and a word position, check to see if still on current word
  2. if still on current word, append letter
  3. if not still on current word, create new word starting with letter
  4. send word into find on trie
  5. Trie does a recursive search through all possible strings that start with the prefix
  6. Creates a priority queue with max at root, pops three times and returns those three

4. Evaluate our accuracy
  - If accurate, increment weight of word by 1
  - If Inaccurate but target is in trie, increment weight of correct word by 1
  - If Inaccurate but target is not in trie, add to trie and set weight to 1

## Testing

#### Test Cases
We tested using the clinton and trump txt files provided by the teacher.  I will create a few more test files of my own out of essays I typed in undergrad.

## Results - Initial Submission

- Here are our initial results with the Clinton txt files.

![Alt text](https://github.com/ryanbomo/SmartWordCSE2010/blob/master/screenshots/test_hilary_1.png?raw=true)

- Here are our initial results with the Trump txt files.

![Alt text](https://github.com/ryanbomo/SmartWordCSE2010/blob/master/screenshots/test_trump_1.png?raw=true)

####Insert can now use variable weights for inserting a word

  - New Hilary results:

![Alt text](https://github.com/ryanbomo/SmartWordCSE2010/blob/master/screenshots/test_hilary_2.png?raw=true)

  - New Trump Results

![Alt text](https://github.com/ryanbomo/SmartWordCSE2010/blob/master/screenshots/test_trump_2.png?raw=true)


## Changes to Initial Submission

####Insert can now use variable weights for inserting a word
Our initial submission had insert add 1 to the words weight every time it was inserted.  However, we needed a way to supress bad guesses from popping back up.  So now, insert caries a weight and this is added to the weight at the node with the last letter of the word.  

This means that we can initialize the words from the dictionary at a set value, and then reward successes more and punish misses less.  Currently values are initialized at 2 per dictionary insert, 1 per prior user insert, 4 per successful find insert, 4 per non-successfuly word terminating insert and -1 for bad guess insert.

While these numbers may seem arbitrary, there is some logic.  We want to allow each word some amoun of buffer for bad guesses, so we initialize the words a bit higher than the baseline.  We want our prior history to matter, but not as much as what is currently being written.  Finally, for our current writes, whether a word is guessed correctly or not, the correct word is given +4 to its weight.  Bad guesses punish a bit, with a -1.  Currently the punishing behavior isn't quite working, but will be updated shortly.
  
Words can only be lowered to 1, so as to keep them distinct from non-word terminating nodes.  We need words to remain guessable, just less likely each time they are incorrect.

## Results - Final Submission

TBA
