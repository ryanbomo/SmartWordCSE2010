/*

  Authors (group members): Ryan Bomalaski
  Email addresses of group members: rbomalaski2015@my.fit.edu
  Group name: 4C

  Course: CSE 2010
  Section: 4

  Description of the overall algorithm:
1. Grab word list and build trie from the word list [Done in SmartWord]
    a. Initial weight for each word is 0 to allow for each word to be a valid guess
        But without having to worry about whether it's been used or not
2. Process the old messages by chopping the file into lines, and then the lines
    into words. [Done in processOldMessages]
        If the word is in the trie, increment its weight by one and restructure trie
        Else put the word in the trie with weight of 1 
3. Guess by traversing the trie and returning highest weighted words that could
    possibly start with letters typed so far
4. Evaluate our accuracy
        If accurate, increment weight of word by 1
        If Inaccurate but target is in trie, increment weight of correct word by 1
        If Inaccurate but target is not in trie, add to trie and set weight to 1


 */
package SmartWord;

public class SmartWord {

    String[] guesses = new String[3];  // 3 guesses from SmartWord

    // initialize SmartWord with a file of English words
    public SmartWord(String wordFile) {
        /*
        Initialize Trie using word list argument
        Initial weight for everything in trie should be 1
            This allows things to be a valid guess from the beginning
        Once the Trie is built, the processOldMessages will bring in the new
        words to modify the weight.
         */
        String lineBreak = System.getProperty("line.separator");
        String[] eachWord = wordFile.split(lineBreak);
        int weight = 0;
        for(String w: eachWord){
            w = w.toLowerCase();
            /*
            Add w to Trie with weight 1
                This weight is added to each letter in the word at the correct position
            Example:
                w = "dips"
                trie =                  d,1
                                       /    \
                                      i,1    r,1
                                     /   \     \
                                    p,1   r,1   [leaf,1]
                                   /       \
                               [leaf,1]     t,1
                                             \
                                             [leaf,1]
                trie.add(w,weight)
                trie =                  d,2
                                       /    \
                                      i,2    r,1
                                     /   \    \
                                    p,2   r,1   [leaf,1]
                                   / \      \
                           [leaf,1]   s,1    t,1
                                      /         \
                                 [leaf,1]      [leaf,1]
            leaf for each will hold actual frequency for that word
            each letter holds the frequency of that letter in that order
            this allows for guesses to be shorter words
            */
            
        }

    }

    // process old messages from oldMessageFile
    public void processOldMessages(String oldMessageFile) {
        String lineBreak = System.getProperty("line.separator");
        String[] individualLines = oldMessageFile.split(lineBreak);
        /*
            Check for Numbers in Word
                If Num:
                    Ignore Word
                Else:
                    Add to Trie
        */
        

    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess(char letter, int letterPosition, int wordPosition) {

        return guesses;
    }

    // feedback on the 3 guesses from the user
    // isCorrectGuess: true if one of the guesses is correct
    // correctWord: 3 cases:
    // a.  correct word if one of the guesses is correct
    // b.  null if none of the guesses is correct, before the user has typed in 
    //            the last letter
    // c.  correct word if none of the guesses is correct, and the user has 
    //            typed in the last letter
    // That is:
    // Case       isCorrectGuess      correctWord   
    // a.         true                correct word
    // b.         false               null
    // c.         false               correct word
    public void feedback(boolean isCorrectGuess, String correctWord) {

    }

}
