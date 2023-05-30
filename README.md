# sit-reeceslade
sit-reeceslade created by GitHub Classroom

Better computer player
In this code i attempted to add in a computer player which randomly shot at generated ships when the player had made a guess on the other board. 
When a player made a guess it would switch to the computerTurn function after a bit of delay and shoot at the players ships.
However after bug issues regarding creating a new instance of HomeView2 i decided to comment this out as i had ran out of time to implement the opponent player
although some functionality is there. I did not get to implement a better computer player so my change difficulty button does nothing.
If i was to implement a better computer player i would make it so it wouldnt guess the same cell twice ad when it hit a ship it wouldnt randomly shoot aimlessly again
i would have it move up, down, left and right until it hit again. When it did, i would continue this pattern until ship is sunk.

Automatic vs manual placement
I included both automatic and manual placement, my desired outcome was to drag and drop ships to given shipPositions, this would be confirmed with a placementBtn
which woudld pass this value to my next activity and update the view regarding the shipPositions.
Then as mentioned earlier the opponent function would attempt to shoot these shipPositions after each player go.
In the end i wanted to demonstrate that i had the functionality of moving ships but couldn't pass this information to update the view in the next activity.
I also included automatic placement through a random generate function which had checks to ensure ships do not go out of grid bounds, or overlap eachother.
This was done so the player could shoot at ships which were randomly generated with feedback.

Rule variations
The only rule variations i included was that if you click a ship on the board this is equal to a hit, if you do not click a ship on the board then this is a miss.
If all the ship sizes are sunk then this triggers a game over activity where a player can either exit or restart.
