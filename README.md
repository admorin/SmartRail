# Project-3-CS351

Alex Schmidt, Tyson Craner, Andrew Morin.

The train simulator we created created. The GUI is drawn from a .txt file inside of the map package in src. It requires a width, a height and then the map using capital letters as stations (Only on the ends of the lanes) dashes to represent regular tracks, and number pairs to represent the top and bottom of switches. (Switches must move right or left; i.e. not straight up or down.)

Known Bugs:

1)The GUI shows the train as slightly off center sometimes. There's an issue with our math that causes this.

2)The pathfinding algorithm won't always find the fastest path possible, but it will find a path.

3)Lights don't always work, but the logic is implemented. The train will stop when it needs to, even if the lights don't reflect that.
