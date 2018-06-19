package com.example.realmwithmvpjava;


import android.app.Fragment;

import com.example.realmwithmvpjava.Main.MainActivity;
import com.example.realmwithmvpjava.Main.MainView;
import com.example.realmwithmvpjava.PostList.LoadItemsInteractor;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MockTestExample {


    @Mock
    MainActivity mainActivity;

    @Mock
    LoadItemsInteractor loadItemsInteractor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void firstTest() {
      //  when(mainActivity.demoMethod()).thenReturn(5);
    }

    @Test
    public void testLinkedListSpyCorrect() {
        // Lets mock a LinkedList
        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);

        // You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);

        assertEquals("foo", spy.get(0));
    }


    @Test
    public void secondTest() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        System.out.println(mockedList.size());
        mockedList.clear();
        System.out.println(mockedList.size());

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }


    @Test public void spyTestOnObject(){
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }


    @Test
    public void testApiMethod(){
        doThrow(new RuntimeException()).when(loadItemsInteractor).getDataFromAPI();
    }


}
