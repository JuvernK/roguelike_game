
**Title**:
Come back stronger 👊

**Description**:

Mario is given 3 lives (hearts) when starting the game. Its life is reduced by 1 each time it dies and the game is restarted automatically after each death, and he will be moved back to his starting location. Mario will be given some buffs depending on the number of hearts left. In addition, a new Hammer weapon will be given to Mario when he has 1 heart left.

**Explanation why it adheres to SOLID principles** :

a) The addition of the ExtraLives interface abides by the Interface Segregation Principle (ISP) since it is small and focused, as it only has 1 method which is the die() method which allows any Actor classes that implement this interface to provide their specific implementations when they are dead.

b) The new Hammer class follows the Single Responsibility Principle (SRP) since the specific purpose of this class is to represent Hammer as a WeaponItem in the game.

**Our pproaches**                                                                                                                               

1) We use three classes from the engine package, which are Location, IntrinsicWeapon and WeaponItem. Firstly, the Player class declares an attribute of type Location and it will be initialized in the constructor in order to be moved back to this Location after the Player dies. Next, a new IntrinsicWeapon instance will be instantiated in the getIntrinsicWeapon() method to update the Player’s punch damage. Lastly, a new Hammer class is created by extending from the abstract WeaponItem class. |
2) ResetAction is used to enable the Player to reset the game. The Player class now has 2 attributes of type ResetAction to implement a manual reset (r command) and auto-reset (after it dies). Furthermore, the execute() method in AttackAction, and tick() method in Fire and Lava class are reused to invoke the die() method of Player.                                                                                                                                                                      |
3) An abstraction "ExtraLives" interface is created in order to allow the Player class to implement the die() method. In addition, the abstract WeaponItem class is used when the Hammer class inherits from it.                                                                                                                                                                      |
4) The RESET_GAME capability is used to perform a manual ResetAction. After a manual reset has been performed, this capability will be removed in the playTurn() method. Additionally, the RESET_CAPABLE capability is used to reset all resettable instances. This capability will be added to the capabilities list of resettable instances whenever a ResetAction is executed (Player dies or perform a manual reset).                                                                                                                                                                      |

---
**Title**: 
Help me, Yoshi! 🦖

**Description**:

Yoshi is Mario's adventure partner and has to be hatched from a Yoshi Egg. The Yoshi Egg is placed in the Forest Zone when the game begins, and Mario has to pick it up and protect it for 7 turns before Yoshi can be hatched from it. If Mario is attacked by an enemy before the egg hatches, the egg will break, and be 
respawned in another random location in the forest zone. Yoshi will act as an ally and have the same HP as Mario (100), it will follow Mario and help to attack enemies by biting them, with the damage being 15 HP. When Yoshi dies, a new Egg will be spawned at a random Location in the Forest Zone. At any given moment, 
there will only be a maximum of 1 YoshiEgg in the World. Lastly, Yoshi can only be in the Forest Zone since it cannot teleport to the Lava Zone.

**Explanation why it adheres to SOLID principles** :

a) The design follows the Single Responsibility Principle (SRP) since creating new YoshiAttackBehaviour and YoshiFollowBehaviour classes for Yoshi ensures we don’t make the existing AttackBehaviour and FollowBehaviour class check whether the actor is an instance of Yoshi, thus not making them have too many responsibilities. Besides, YoshiAttackBehaviour and YoshiFollowBehaviour will also implement behaviours that are specific only to Yoshi.

b) Besides, this design complements the Open-Closed Principle, since the YoshiEgg class extends from the abstract Item class, therefore the engine code is not modified and additional features can be added to implement new functionalities such as spawning a new Egg on the map.

**Our pproaches**                                                                                                                               
1) We use 2 classes from the engine package, which are Actor and Item. Yoshi class extends from Actor class and YoshiEgg class extends from Item class. This is to show Yoshi is an Actor and YoshiEgg is an Item relationships, and we want to reuse methods from these classes.                                                                                                                                                                                             2) Wherever the player goes, Yoshi follows and as a result, Yoshi must jump when the actor jumps to a jumpable ground. For the jumps actions, Yoshi reuses the JumpAction class in its YoshiFollowBehaviour class, and also follows the success rate and fall damage of the ground when Yoshi jumps. Additionally, Yoshi implements the Resettable interface, when it is reset, it will be removed from the map and a new YoshiEgg will be spawned randomly on the same map.

3) In Yoshi's attack behaviour, Yoshi checks whether or not the target is an instance of the Enemy abstract class and returns new attack action if true. Furthermore, new behaviours that are created, such as YoshiAttackBehaviour class and YoshiFollowBehaviour class implement the existing Behaviour interface.                                                                                                                                                                                      |
| Must use existing or create new capabilities                                                                            | Yoshi reuses the HOSTILE_TO_ENEMY capability, thus it can attack the enemies and be attacked. Next, a new NOSHI capability is added to symbolize the egg breaking when the actor is carrying it. In addition, a new EGG capability is added to YoshiEgg so that the actor carrying this egg will have this status.                                                                                                                                                                                                                          |                                                                                |
