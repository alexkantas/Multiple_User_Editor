/*
 * Copyright 2016 Alexandros Kantas .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.alexkantas.multipleusereditor;

import java.util.Observable;

/**
 *
 * @author Alexandros Kantas
 */
public class Model extends Observable {

    private String text = "HI!";

    private int i = 1;

    public synchronized String getText() {
        return text;
    }

    public synchronized void setText(String text) {
        this.text = text;
            setChanged();
            notifyObservers();
    }

    public synchronized void addViewer() {
            Viewer v = new Viewer("Viewer #" + i, this);
            Thread tv = new Thread(v);
            tv.start();
            i++;
            addObserver(v);
            System.out.println("" + getListsize());
            setChanged();
            notifyObservers();

    }

    public synchronized void addViewer(Viewer viewer) {
            addObserver(viewer);
            System.out.println("" + getListsize());
            setChanged();
            notifyObservers();
    }

    public synchronized void removeViewer(Viewer viewer) {
            deleteObserver(viewer);
            setChanged();
            notifyObservers();
    }

    public synchronized int getListsize() {
        return countObservers() - 1;
    }

}
