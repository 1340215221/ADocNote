package com.rh.note.view.layout

import com.rh.note.util.ISwingBuilder
import com.rh.note.view.SwingBuilderImpl

import java.awt.*

class BorderLayoutBuilder implements ISwingBuilder {

    private Component center;
    private Component north;
    private Component east;
    private Component south;
    private Component west;

    void layout(SwingBuilderImpl impl) {
        swingBuilder.borderLayout(id: "${impl.id}_layout",
                center: center,
                north: north,
                east: east,
                west: west,
                south: south,
        )
    }

    private void center(Closure run) {
        if (run == null) {
            return
        }

        def delegate = run.delegate.delegate
        if (delegate != this) {
            delegate.center(run)
            return
        }

        def component = run()
        if (component == null) {
            return
        }

        center = component
    }

    private void north(Closure run) {
        if (run == null) {
            return
        }

        def delegate = run.delegate.delegate
        if (delegate != this) {
            delegate.north(run)
            return
        }

        def component = run()
        if (component == null) {
            return
        }

        north = component
    }

    private void east(Closure run) {
        if (run == null) {
            return
        }

        def delegate = run.delegate.delegate
        if (delegate != this) {
            delegate.east(run)
            return
        }

        def component = run()
        if (component == null) {
            return
        }

        east = component
    }

    private void south(Closure run) {
        if (run == null) {
            return
        }

        def delegate = run.delegate.delegate
        if (delegate != this) {
            delegate.south(run)
            return
        }

        def component = run()
        if (component == null) {
            return
        }

        south = component
    }

    private void west(Closure run) {
        if (run == null) {
            return
        }

        def delegate = run.delegate.delegate
        if (delegate != this) {
            delegate.west(run)
            return
        }

        def component = run()
        if (component == null) {
            return
        }

        west = component
    }

}
