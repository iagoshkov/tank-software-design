package ru.mipt.bit.platformer.entity.level;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.tree.Tree;
import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.tank.Direction;

import java.util.ArrayList;
import java.util.List;


public class Level {

    private Tank tank;
        private final List<Tree> trees  = new ArrayList<Tree>();

    public Level() {
            this.addTank();
            this.addTrees();
        }

        public void addTank() {
            tank = new Tank(new GridPoint2(1, 1));
        }

            public void addTrees() {
                Tree tree1 = new Tree(new GridPoint2(1, 3));

                trees.add(tree1);

            }

                    public void moveTank(Direction direction) {
                        if (direction != Direction.NULL) {
                            GridPoint2 point = new GridPoint2(tank.getCoordinates());
                            GridPoint2 directionPoint = direction.getDirectionPoint();
                            point = point.add(directionPoint.x, directionPoint.y);
                            if (!inPointIsTree(point) && isEqual(tank.getMovementProgress(), 1f)) {
                                tank.move(directionPoint, direction.getDirectionRotation());
                            }
                        }
                    }
                    public boolean inPointIsTree(GridPoint2 point) {
                        for (Tree tree : trees) {
                            if (tree.getCoordinates().equals(point)) {
                                return true;
                            }
                        }
                        return false;
                    }

                        public Tank getTank() {
                            return tank;
                        }

                            public List<Tree> getTress() {
                                return trees;
                            }
                        }
