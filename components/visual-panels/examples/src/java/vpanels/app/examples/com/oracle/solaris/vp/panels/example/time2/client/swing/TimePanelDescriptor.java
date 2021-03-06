/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at usr/src/OPENSOLARIS.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright (c) 2010, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.solaris.vp.panels.example.time2.client.swing;

import com.oracle.solaris.adr.Stability;
import com.oracle.solaris.vp.panel.common.*;
import com.oracle.solaris.vp.panel.common.control.*;
import com.oracle.solaris.vp.panel.common.model.*;
import com.oracle.solaris.vp.panel.swing.control.PanelFrameControl;
import com.oracle.solaris.vp.panel.swing.model.SwingPanelDescriptor;
import com.oracle.solaris.vp.panels.example.time2.*;
import com.oracle.solaris.vp.panels.example.time2.common.TimeUtil;
import com.oracle.solaris.vp.util.misc.finder.Finder;

public class TimePanelDescriptor
    extends AbstractPanelDescriptor<ManagedObject>
    implements SwingPanelDescriptor<ManagedObject> {

    //
    // Instance data
    //

    private DefaultControl control;
    private MXBeanTracker<TimeMXBean> beanTracker;

    //
    // Constructors
    //

    /**
     * Constructs a {@code TimePanelDescriptor}.
     *
     * @param	    id
     *		    a unique identifier for this Panel, taken from the panel
     *		    registration
     *
     * @param	    context
     *		    a handle to interact with the Visual Panels client
     */
    public TimePanelDescriptor(String id, ClientContext context)
	throws TrackerException {

	super(id, context);

	control = new PanelFrameControl<TimePanelDescriptor>(this);
	control.addChildren(new TimeControl(this));

	beanTracker = new MXBeanTracker<TimeMXBean>(
	    TimeUtil.OBJECT_NAME, TimeMXBean.class, Stability.PRIVATE, context);
    }

    //
    // ManagedObject methods
    //

    /**
     * Stops monitoring the connection to the remote host.
     */
    @Override
    public void dispose() {
	beanTracker.dispose();
	super.dispose();
    }

    @Override
    public String getName() {
	return Finder.getString("panel.time.name");
    }

    //
    // PanelDescriptor methods
    //

    @Override
    public Control getControl() {
	return control;
    }

    //
    // TimePanelDescriptor methods
    //

    public TimeMXBean getTimeBean() {
	return beanTracker.getBean();
    }
}
