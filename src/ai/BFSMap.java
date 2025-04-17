package ai;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class BFSMap implements Serializable {

    private static class Node implements Serializable {
        private static class Path implements Serializable {
            PieceAndDirection move;
            Node dest;

            public Path(PieceAndDirection move, Node dest) {
                this.move = move;
                this.dest = dest;
            }
        }

        ArrayList<Path> paths;
        ArrayList<PieceAndPos> piecesAndPoses;
        int distToDestination = -1;
        int depth;

        public Node(ArrayList<PieceAndPos> piecesAndPoses, int depth) {
            paths = new ArrayList<>();
            this.piecesAndPoses = new ArrayList<>(piecesAndPoses);
            this.depth = depth;
        }
    }

    PieceAndPos winCondition;
    ArrayList<Node> nodeList;
    ArrayList<Node> destinationList;
    GameBoard gameBoard;

    public BFSMap(Setting setting) throws Exception {
        winCondition = setting.winCondition;
        gameBoard = new GameBoard(setting.height, setting.width);
        for (PieceAndPos pieceAndPos : setting.piecesAndPoses) {
            gameBoard.put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        }
        nodeList = new ArrayList<>();
        nodeList.add(new Node(gameBoard.piecesAndPoses, 0));
        destinationList = new ArrayList<>();

        search(0);
    }

    public void search(int index) throws Exception {
        if (index >= nodeList.size()) {
            System.out.println("Finish at depth " + nodeList.get(index - 1).depth + " and index " + (index - 1));
            differentiate();
            return;
        }

        if (index == 0 || nodeList.get(index).depth > nodeList.get(index - 1).depth) {
            System.out.println("Get to depth " + nodeList.get(index).depth + ", at index " + index);
        }

        ArrayList<PieceAndPos> piecesAndPoses = nodeList.get(index).piecesAndPoses;
        Node currentNode = nodeList.get(index);
        gameBoard.clear();
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            gameBoard.put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        }

        if (gameBoard.pieceAtPos(winCondition.piece, winCondition.h, winCondition.w)) {
            destinationList.add(currentNode);
//            gameBoard.print();
        }
//        gameBoard.print();

        ArrayList<PieceAndDirection> possibleMoves = gameBoard.getAllPossibleMoves();
        for (PieceAndDirection move : possibleMoves) {
            gameBoard.move(move.piece, move.direction);
            int exist = exist(gameBoard.piecesAndPoses);
            if (exist == -1) {
                Node newNode = new Node(gameBoard.piecesAndPoses, currentNode.depth + 1);
                nodeList.add(newNode);
                currentNode.paths.add(new Node.Path(move, newNode));
                newNode.paths.add(new Node.Path(new PieceAndDirection(move.piece, move.direction.reverse()), currentNode));
            } else {
                Node sameNode = nodeList.get(exist);
                currentNode.paths.add(new Node.Path(move, sameNode));
                sameNode.paths.add(new Node.Path(new PieceAndDirection(move.piece, move.direction.reverse()), currentNode));
            }
            gameBoard.move(move.piece, move.direction.reverse());
        }

        index += 1;
        search(index);
    }

    private void differentiate() throws Exception {
        System.out.println("Finding " + destinationList.size() + " destinations");
        for (Node node : destinationList) {
            bfs(node);
        }
    }

    private void bfs(Node destinationNode) throws Exception {
        ArrayList<Node> queue = new ArrayList<>();
        destinationNode.distToDestination = 0;
        queue.add(destinationNode);
        while (!queue.isEmpty()) {
            Node currentNode = queue.removeFirst();
            for (Node.Path path : currentNode.paths) {
                if (path.dest.distToDestination == -1 || path.dest.distToDestination > currentNode.distToDestination + 1) {
                    path.dest.distToDestination = currentNode.distToDestination + 1;
                    queue.add(path.dest);
                }
            }
        }
    }

    public int exist(ArrayList<PieceAndPos> piecesAndPoses) {
        for (int i = 0; i < nodeList.size(); i++) {
            ArrayList<PieceAndPos> existPiecesAndPoses = nodeList.get(i).piecesAndPoses;
            if (same(piecesAndPoses, existPiecesAndPoses)) {
                return i;
            }
        }
        return -1;
    }

    public static PieceAndPos diff(ArrayList<PieceAndPos> piecesAndPosesA, ArrayList<PieceAndPos> piecesAndPosesB) throws Exception {
        for (PieceAndPos pieceAndPos : piecesAndPosesA) {
            boolean found = false;
            for (PieceAndPos andPos : piecesAndPosesB) {
                if (same(pieceAndPos, andPos)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return pieceAndPos;
            }
        }
        throw new Exception("无法找到对应的棋子坐标！");
    }

    public static boolean same(ArrayList<PieceAndPos> piecesAndPosesA, ArrayList<PieceAndPos> piecesAndPosesB) {
        for (PieceAndPos pieceAndPos1 : piecesAndPosesA) {
            boolean found = false;
            for (PieceAndPos pieceAndPos2 : piecesAndPosesB) {
                if (same(pieceAndPos1, pieceAndPos2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public static boolean same(PieceAndPos pieceAndPos1, PieceAndPos pieceAndPos2) {
        return pieceAndPos1.piece.width == pieceAndPos2.piece.width &&
                pieceAndPos1.piece.height == pieceAndPos2.piece.height &&
                pieceAndPos1.h == pieceAndPos2.h && pieceAndPos1.w == pieceAndPos2.w;
    }

    public PieceAndDirection query(GameBoard gameBoard) throws Exception {
        if (this.gameBoard.occupancyMap.length != gameBoard.occupancyMap.length
                || this.gameBoard.occupancyMap[0].length != gameBoard.occupancyMap[0].length) {
            throw new Exception("地图大小不匹配！");
        }

        for (Node node : nodeList) {
            ArrayList<PieceAndPos> piecesAndPoses = node.piecesAndPoses;
            if (same(piecesAndPoses, gameBoard.piecesAndPoses)) {
                Node nextNode = null;
                PieceAndDirection move = null;
                for (Node.Path path : node.paths) {
                    if (path.dest.distToDestination < node.distToDestination) {
                        nextNode = path.dest;
                        move = path.move;
                        break;
                    }
                }
                if (nextNode == null) {
                    throw new Exception("建图算法出现错误！");
                }
                PieceAndPos targetPieceAndPos = diff(node.piecesAndPoses, nextNode.piecesAndPoses);
                for (PieceAndPos pieceAndPos : gameBoard.piecesAndPoses) {
                    if (same(pieceAndPos, targetPieceAndPos)) {
                        return new PieceAndDirection(pieceAndPos.piece, move.direction);
                    }
                }

                throw new Exception("没有找到匹配的棋子！");
            }
        }
        throw new Exception("在BFSMap中没找到对应的情况！");
    }

    public static BFSMap load(String file) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        BFSMap map = (BFSMap) in.readObject();
        in.close();
        return map;
    }

    public static void main(String[] args) throws Exception {
        BFSMap map = new BFSMap(new Setting("横刀立马"));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/BFSMap/map.ser"));
        out.writeObject(map);
        out.close();
    }
}
