package it.polimi.ingsw.controller;

/**
 * Enum State all the possible states of the match
 */
public enum State {
    /**
     * Start state.
     */
    START,
    /**
     * Setup state.
     */
    SETUP,
    /**
     * Select state.
     */
    SELECT,
    /**
     * Placeworkers state.
     */
    PLACEWORKERS,
    /**
     * Startturn state.
     */
    STARTTURN,
    /**
     * Selectworker state.
     */
    SELECTWORKER,
    /**
     * Beforemove state.
     */
    BEFOREMOVE,
    /**
     * Move state.
     */
    MOVE,
    /**
     * Build state.
     */
    BUILD,
    /**
     * Endmatch state.
     */
    ENDMATCH,
    /**
     * Lobby state.
     */
    LOBBY,
}
